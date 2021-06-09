package code.flatura.edukpi.model.DTO;


import code.flatura.edukpi.model.Fact;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FactDto {//implements AbstractDto {
    private final String id;
    private String employeeName;
    private String employeeId;
    private String indicatorName;
    private String indicatorId;
    private int pointsSuggested;
    private String description;
    private LocalDateTime dateTimeCreated;
    private int pointsApproved;
    private String signerName;
    private String signerId;
    private String signerDescription;

    public FactDto(String id, String employeeName, String employeeId, String indicatorName, String indicatorId, int pointsSuggested, String description, LocalDateTime dateTimeCreated, int pointsApproved, String signerName, String signerId, String signerDescription) {
        this.id = id;
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.indicatorName = indicatorName;
        this.indicatorId = indicatorId;
        this.pointsSuggested = pointsSuggested;
        this.description = description;
        this.dateTimeCreated = dateTimeCreated;
        this.pointsApproved = pointsApproved;
        this.signerName = signerName;
        this.signerId = signerId;
        this.signerDescription = signerDescription;
    }

    public FactDto() {
        id = null;
    }

    public String getId() {
        return id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public int getPointsSuggested() {
        return pointsSuggested;
    }

    public String getDescription() {
        return description;
    }

    public int getPointsApproved() {
        return pointsApproved;
    }

    public String getSignerName() {
        return signerName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public void setPointsSuggested(int pointsSuggested) {
        this.pointsSuggested = pointsSuggested;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public void setPointsApproved(int pointsApproved) {
        this.pointsApproved = pointsApproved;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public void setSignerName(String signerName) {
        this.signerName = signerName;
    }

    public String getSignerId() {
        return signerId;
    }

    public void setSignerId(String signerId) {
        this.signerId = signerId;
    }

    public String getSignerDescription() {
        return signerDescription;
    }

    public void setSignerDescription(String signerDescription) {
        this.signerDescription = signerDescription;
    }

    public static FactDto convertFrom(Fact f) {
        return new FactDto(f.getId().toString(),
                f.getUser().getSurname()+ " " + f.getUser().getName(),
                f.getUser().getId().toString(),
                f.getIndicator().getName(),
                f.getIndicator().getId().toString(),
                f.getPointsSuggested(),
                f.getDescription(),
                f.getCreated(),
                f.getPointsApproved(),
                f.getSigner()==null?"":(f.getSigner().getSurname() + " " + f.getSigner().getName()),
                f.getSigner()==null?"":f.getSigner().getId().toString(),
                f.getSignerDescription()
                );
    }

    public static List<FactDto> multipleConvertFrom(List<Fact> facts) {
        List<FactDto> result = new ArrayList<>();
        for(Fact f : facts) result.add(convertFrom(f));
        return result;
    }

    @Override
    public String toString() {
        return "FactDto{" +
                "id=" + id +
                ", employeeName='" + employeeName + '\'' +
                ", employeeId=" + employeeId +
                ", indicatorName='" + indicatorName + '\'' +
                ", indicatorId=" + indicatorId +
                ", pointsSuggested=" + pointsSuggested +
                ", description='" + description + '\'' +
                ", pointsApproved=" + pointsApproved +
                ", signerName='" + signerName + '\'' +
                '}';
    }
}