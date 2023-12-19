package model;

public class Service {
    private String name;

    private String service;
    private String abcD;

    private boolean containsFlag;

    public boolean isContainsFlag() {
        return containsFlag;
    }

    public void setContainsFlag(boolean containsFlag) {
        this.containsFlag = containsFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    public String getAbcD() {
        return abcD;
    }

    public void setAbcD(String abcD) {
        this.abcD = abcD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
