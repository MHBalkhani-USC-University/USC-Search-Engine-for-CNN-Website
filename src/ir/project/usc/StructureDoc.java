package ir.project.usc;

public class StructureDoc {

    public StructureDoc(

        String name,
        String type,
        boolean store

    ){

        this.name = name;
        this.type = type;
        this.store = store;

    }

    private String name;
    private String type;
    private boolean store;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStore() {
        return store;
    }

    public void setStore(boolean store) {
        this.store = store;
    }


}
