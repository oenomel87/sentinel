package io.oenomel.sentinel.service.analyzer;

import io.oenomel.sentinel.LinkLogAction;
import io.oenomel.sentinel.entity.Child;
import io.oenomel.sentinel.entity.Link;
import io.oenomel.sentinel.entity.LinkLog;
import io.oenomel.sentinel.entity.Payment;
import io.oenomel.sentinel.repository.ChildRepository;
import io.oenomel.sentinel.repository.LinkLogRepository;
import io.oenomel.sentinel.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AnalyzerImpl implements Analyzer {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private LinkLogRepository linkLogRepository;

    @Value("${sentinel.link.maxamount}")
    private int MAXLINKAMOUNT;

    @Override
    public void analyzing() {
        saveLinkLogs(breakLinks());
    }

    private List<LinkLog> breakLinks() {
        var brokenLinks = new ArrayList<LinkLog>();
        brokenLinks.addAll(breakDischargedChildLinks());
        brokenLinks.addAll(breakStopDonationLinks());
        brokenLinks.addAll(breakExcessiveAmountLink());
        return brokenLinks;
    }

    private List<LinkLog> breakDischargedChildLinks() {
        var brokenLinks = linkRepository.findAll()
                .parallelStream()
                .filter(link -> checkChildStatus(link.getChild()))
                .collect(Collectors.toList());
        linkRepository.deleteInBatch(brokenLinks);
        return brokenLinks.parallelStream()
                .map(link -> new LinkLog(link, LinkLogAction.DELETE, "수혜자 상태 변경"))
                .collect(Collectors.toList());
    }

    private boolean checkChildStatus(Child child) {
        return "Y".equals(child.getOrg().getStatus()) && "Y".equals(child.getStatus());
    }

    private List<LinkLog> breakStopDonationLinks() {
        var brokenLinks = linkRepository.findAll()
                .parallelStream()
                .filter(link -> checkPaymentStatus(link.getPayment()))
                .collect(Collectors.toList());
        linkRepository.deleteInBatch(brokenLinks);
        return brokenLinks.parallelStream()
                .map(link -> new LinkLog(link, LinkLogAction.DELETE, "후원 상태 변경"))
                .collect(Collectors.toList());
    }

    private boolean checkPaymentStatus(Payment p) {
        return "20".equals(p.getPaymentStatus()) || "25".equals(p.getPaymentStatus())
                || "50".equals(p.getPaymentStatus()) || "60".equals(p.getPaymentStatus())
                || "70".equals(p.getPaymentStatus());
    }

    private List<LinkLog> breakExcessiveAmountLink() {
        var brokenLinks = childRepository.findAll()
                .parallelStream()
                .filter(child -> child.sumOfLinkAmount() > MAXLINKAMOUNT)
                .flatMap(this::alignLinkByAmount)
                .collect(Collectors.toList());
        linkRepository.deleteInBatch(brokenLinks);
        return brokenLinks.parallelStream()
                .map(link -> new LinkLog(link, LinkLogAction.DELETE, "결연금액 초과"))
                .collect(Collectors.toList());
    }

    private Stream<Link> alignLinkByAmount(Child child) {
        if (child.validLinkAmount() > MAXLINKAMOUNT) {
            child.getSmallestAmountLink().setStatus("N");
            return alignLinkByAmount(child);
        }
        return child.getLinks().stream()
                .filter(link -> "N".equals(link.getStatus()));
    }

    private void saveLinkLogs(List<LinkLog> linkLogs) {
        linkLogRepository.saveAll(linkLogs);
    }
}
