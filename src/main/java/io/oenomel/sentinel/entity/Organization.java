package io.oenomel.sentinel.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "organizations_info")
public class Organization {

    @Id
    @Column(name = "organizations_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orgId;

    @Column(name = "organizations_name")
    private String orgName;

    private String status;

    private String address;

    @OneToMany(mappedBy = "org")
    private List<Child> children;

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}
