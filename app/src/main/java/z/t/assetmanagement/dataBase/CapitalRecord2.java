package z.t.assetmanagement.dataBase;

import org.litepal.crud.LitePalSupport;

import java.util.Date;


public class CapitalRecord2 extends LitePalSupport {
    private String unid;
    private Date createdate;
    private Date versiondate;
    private Double amount;
    private String status;
    private String tpye;
    private String tpyeName;
    private String name;
    private String level;
    private String phase;
    private String change;
    private String remark;

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getVersiondate() {
        return versiondate;
    }

    public void setVersiondate(Date versiondate) {
        this.versiondate = versiondate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTpye() {
        return tpye;
    }

    public void setTpye(String tpye) {
        this.tpye = tpye;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTpyeName() {
        return tpyeName;
    }

    public void setTpyeName(String tpyeName) {
        this.tpyeName = tpyeName;
    }
}
