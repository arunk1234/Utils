package model;
public class Adapter {
    private String name;
    private String abcd;

    public String getAbcd() {
        return abcd;
    }
    private boolean containsFlag;

    public boolean isContainsFlag() {
        return containsFlag;
    }

    public void setContainsFlag(boolean containsFlag) {
        this.containsFlag = containsFlag;
    }

    public void setAbcd(String abcd) {
        this.abcd = abcd;
    }

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdapter() {
        return adapter;
    }

    public void setAdapter(String adapter) {
        this.adapter = adapter;
    }

    private String adapter;
}
