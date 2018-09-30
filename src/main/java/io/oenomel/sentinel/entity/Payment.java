package io.oenomel.sentinel.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@Table(name = "payment_info")
public class Payment {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int paymentId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "payment_individual")
    private String paymentIndividual;

    @Column(name = "payment_division")
    private String paymentDivision;

    @Column(name = "payment_amount")
    private int amount;

    @Column(name = "agreement_date")
    private String agreementDate;

    @Column(name = "first_approval_date")
    private String firstApprovalDate;

    @Column(name = "approval_date")
    private String approvalDate;

    @Column(name = "payment_period")
    private int period;

    private String memo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "payment", cascade = CascadeType.ALL)
    private List<Link> links;

    @Transient
    private int newTotalAmount;

    @Transient
    private List<Link> newLinks = new ArrayList<>();

    public int sumOfLinkAmount() {
        if(links == null || links.isEmpty()) {
            return 0;
        }
        return links.stream().mapToInt(l -> l.getAmount()).sum();
    }

    public int normalizeAmount() {
        if(paymentDivision.equals("TEMPORARY")) {
            return (int) Math.ceil(amount / 12.0);
        }
        return amount;
    }

    public int amountForDESCSorting() {
        return -1 * this.amount;
    }

    public int sortingByMemo() {
        return memo != null && Pattern.compile("(아이|원함|원하|아동|수혜)+").matcher(memo).find() ? -1 : 1;
    }

    public void setNewTotalAmount(int newTotalAmount) {
        this.newTotalAmount = newTotalAmount;
    }

    public int getNewTotalAmount() {
        return newTotalAmount;
    }

    public int availableAmount() {
        return normalizeAmount() - newTotalAmount;
    }

    public void newLink(int amount, Child c) {
        Link newLink = new Link();
        newLink.setAmount(amount);
        newLink.setChild(c);
        newLink.setMemberId(member.getMemberId());
        newLink.setPayment(this);
        newLink.setStatus("Y");
        newLinks.add(newLink);
        newTotalAmount = newTotalAmount + amount;
        c.setNewTotalAmount(c.getNewTotalAmount() + amount);
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentIndividual() {
        return paymentIndividual;
    }

    public void setPaymentIndividual(String paymentIndividual) {
        this.paymentIndividual = paymentIndividual;
    }

    public String getPaymentDivision() {
        return paymentDivision;
    }

    public void setPaymentDivision(String paymentDivision) {
        this.paymentDivision = paymentDivision;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(String agreementDate) {
        this.agreementDate = agreementDate;
    }

    public String getFirstApprovalDate() {
        return firstApprovalDate;
    }

    public void setFirstApprovalDate(String firstApprovalDate) {
        this.firstApprovalDate = firstApprovalDate;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Link> getNewLinks() {
        return newLinks;
    }

    public void setNewLinks(List<Link> newLinks) {
        this.newLinks = newLinks;
    }
}
