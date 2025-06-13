package com.bdp.Cafa.logApproval.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "approval_change_status_logs")
public class LogList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @Column(name = "changes_id")
    private Integer changesId;

    @Column(name = "log_timestamp")
    private LocalDateTime logTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_type")
    private ChangeTypeEnum changeType;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_status")
    private ChangeStatusEnum changeStatus;

    public enum ChangeTypeEnum {
        CREATE,
        UPDATE,
        DELETE
    }

    public enum ChangeStatusEnum {
        PENDING,
        APPROVED,
        REJECTED,
        APPLIED
    }

    public LogList() {
    }

    public LogList(Integer changesId, ChangeTypeEnum changeType, String message, ChangeStatusEnum changeStatus) {
        this.changesId = changesId;
        this.changeType = changeType;
        this.message = message;
        this.changeStatus = changeStatus;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Integer getChangesId() {
        return changesId;
    }

    public void setChangesId(Integer changesId) {
        this.changesId = changesId;
    }

    public LocalDateTime getLogTimestamp() {
        return logTimestamp;
    }

    public void setLogTimestamp(LocalDateTime logTimestamp) {
        this.logTimestamp = logTimestamp;
    }

    public ChangeTypeEnum getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeTypeEnum changeType) {
        this.changeType = changeType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChangeStatusEnum getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(ChangeStatusEnum changeStatus) {
        this.changeStatus = changeStatus;
    }
}