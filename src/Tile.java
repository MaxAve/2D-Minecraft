public class Tile {
    public static java.util.ArrayList<Tile> tileInstances = new java.util.ArrayList<>();

    // Save data
    char[] saveID; // The ID by which the tile will be saved in world files (2 chars)
    byte allocID; // Worlds are saved using text characters, however, we can use a single 8-bit number to display loaded tiles to save memory

    // Properties
    String name; // Tile name to be displayed in-game

    public Tile(String n, char[] sid, byte aid) {
        this.name = n;
        if(sid.length == 2)
            this.saveID = sid;
        else
            throw new IllegalArgumentException("saveID can only contain 2 values");
        this.allocID = aid;
        if(this.auth()) tileInstances.add(this);
    }

    private boolean auth() {
        boolean valid = true;
        for(Tile t : tileInstances) {
            if(t.name.equals(this.name)) {
                valid = false;
                throw new IllegalArgumentException("A tile with the name " + t.name + " already exists!");
            }
            if(t.saveID.equals(this.saveID)) {
                valid = false;
                throw new IllegalArgumentException("A tile with the saveID " + t.saveID[0] + t.saveID[1] + " already exists!");
            }
            if(t.allocID == this.allocID) {
                valid = false;
                throw new IllegalArgumentException("A tile with the allocID " + t.allocID + " already exists!");
            }
        }
        return valid;
    }
}
