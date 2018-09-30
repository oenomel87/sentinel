package io.oenomel.sentinel.entity;

import javax.persistence.*;

@Entity
@Table(name = "relationship_info")
public class Link {

    @Id
    @Column(name = "relationship_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int linkId;

    @Column(name = "member_id")
    private int memberId;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "beneficiary_id")
    private Child child;

    @Column(name = "relationship_amount")
    private int amount;

    @Transient
    private String status;

    private String regdate;

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }
}
