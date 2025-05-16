package workingWithFiles.modeli;

public class PersonalObject {

    private String name;
    private Integer height;
    private Integer width;
    private PersonalInnerObject equipment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public PersonalInnerObject getEquipment() {
        return equipment;
    }

    public void setEquipment(PersonalInnerObject equipment) {
        this.equipment = equipment;
    }
}
