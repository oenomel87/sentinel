package io.oenomel.sentinel.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "beneficiary_info")
public class Child {

    @Id
    @Column(name = "beneficiary_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int childId;

    @Column(name = "beneficiary_name")
    private String childName;

    @Column(name = "rainbow_id")
    private String rainbowId;

    private String gender;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "beneficiary_type")
    private String type;

    @Column(name = "beneficiary_status")
    private String status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "child", cascade = CascadeType.ALL)
    private List<Link> links;

    @ManyToOne
    @JoinColumn(name = "organizations_id")
    private Organization org;

    @Transient
    private int newTotalAmount;

    public int sumOfLinkAmount() {
        if(links == null || links.isEmpty()) {
            return 0;
        }
        return links.stream().mapToInt(l -> l.getAmount()).sum();
    }

    public int validLinkAmount() {
        if(links == null || links.isEmpty()) {
            return 0;
        }
        return links.stream().filter(l -> "Y".equals(l.getStatus())).mapToInt(l -> l.getAmount()).sum();
    }

    public Link getSmallestAmountLink() {
        return links.stream().sorted(Comparator.comparing(Link::getAmount)).findFirst().get();
    }

    public void setNewTotalAmount(int newTotalAmount) {
        this.newTotalAmount = newTotalAmount;
    }

    public int getNewTotalAmount() {
        return this.newTotalAmount;
    }

    public int getAge() {
        return LocalDate.now().getYear() - LocalDate.parse(this.birthDate).getYear() + 1;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getRainbowId() {
        return rainbowId;
    }

    public void setRainbowId(String rainbowId) {
        this.rainbowId = rainbowId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }
}
