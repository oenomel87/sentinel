package io.oenomel.sentinel.entity;

import io.oenomel.sentinel.LinkLogAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "link_log")
public class LinkLog {

    public LinkLog(Link link, LinkLogAction action) {
        setLink(link);
        if(LinkLogAction.ADD == action) {
            log = "자동추가";
            this.action = action.name();
        } else if(LinkLogAction.DELETE == action) {
            log = "자동삭제";
            this.action = action.name();
        }
    }

    public LinkLog(Link link, LinkLogAction action, String log) {
        this.setLink(link);
        if(LinkLogAction.ADD == action) {
            log = "자동추가-" + log;
            this.action = action.name();
        } else if(LinkLogAction.DELETE == action) {
            log = "자동삭제-" + log;
            this.action = action.name();
        }
    }

    private void setLink(Link link) {
        memberId = link.getPayment().getMember().getMemberId();
        paymentId = link.getPayment().getPaymentId();
        childId = link.getChild().getChildId();
        amount = link.getAmount();
        linkDate = link.getRegdate() == null
                ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : link.getRegdate();
        memberName = link.getPayment().getMember().getMemberName();
        normalizePaymentAmount = link.getPayment().normalizeAmount();
        childName = link.getChild().getChildName();
        orgName = link.getChild().getOrg().getOrgName();
    }

    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int logId;

    @Column(name = "member_id")
    private int memberId;

    @Column(name = "payment_id")
    private int paymentId;

    @Column(name = "beneficiary_id")
    private int childId;

    private int amount;

    private String action;

    private String log;

    @Column(name = "link_date")
    private String linkDate;

    @Transient
    private String childName;

    @Transient
    private String orgName;

    @Transient
    private String memberName;

    @Transient
    private int normalizePaymentAmount;

    public String linkLogMessage() {
        return "[" + action + "]" + memberName + "[" + paymentId + "|" + normalizePaymentAmount+ "]" + "+" + childName + "[" + orgName + "]"
                + "=" + amount + ">" + log;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getLinkDate() {
        return linkDate;
    }

    public void setLinkDate(String linkDate) {
        this.linkDate = linkDate;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getNormalizePaymentAmount() {
        return normalizePaymentAmount;
    }

    public void setNormalizePaymentAmount(int normalizePaymentAmount) {
        this.normalizePaymentAmount = normalizePaymentAmount;
    }
}
