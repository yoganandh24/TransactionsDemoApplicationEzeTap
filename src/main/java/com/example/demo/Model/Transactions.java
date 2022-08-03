package com.example.demo.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reposting")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Column(name = "txn_id")
    private String txnId;

    @Column(name = "external_ref_number")
    private String externalRefNumber;

    @Column(name = "posting_status")
    private int postingStatus;

    @Column(name = "posting_count")
    private int postingCount;

    @Column(name = "txn")
    private String txn;

    @Column(name = "posting_request")
    private String postingRequest;

    @Column(name = "posting_response")
    private String postingResponse;

    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name ="updated_date")
    private Date updatedDate;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_mode")
    private String paymentMode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getExternalRefNumber() {
        return externalRefNumber;
    }

    public void setExternalRefNumber(String externalRefNumber) {
        this.externalRefNumber = externalRefNumber;
    }

    public int getPostingStatus() {
        return postingStatus;
    }

    public void setPostingStatus(int postingStatus) {
        this.postingStatus = postingStatus;
    }

    public int getPostingCount() {
        return postingCount;
    }

    public void setPostingCount(int postingCount) {
        this.postingCount = postingCount;
    }

    public String getTxn() {
        return txn;
    }

    public void setTxn(String txn) {
        this.txn = txn;
    }

    public String getPostingRequest() {
        return postingRequest;
    }

    public void setPostingRequest(String postingRequest) {
        this.postingRequest = postingRequest;
    }

    public String getPostingResponse() {
        return postingResponse;
    }

    public void setPostingResponse(String postingResponse) {
        this.postingResponse = postingResponse;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id=" + id +
                ", txnId='" + txnId + '\'' +
                ", externalRefNumber='" + externalRefNumber + '\'' +
                ", postingStatus=" + postingStatus +
                ", postingCount=" + postingCount +
                ", txn='" + txn + '\'' +
                ", postingRequest='" + postingRequest + '\'' +
                ", postingResponse='" + postingResponse + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", status='" + status + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                '}';
    }
}
