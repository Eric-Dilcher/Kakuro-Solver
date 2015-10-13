public class Field {

    private final boolean isAdjustable; // true if blank adjustable field
    private final int down; // stores value of sum required below
    private final int across; // stores value of sum required right
    private int playerValue; // store the value of an adjustable field

    public Field() { // def. constructor. Assumes adjustable
        this.isAdjustable = true;
        this.down = 0;
        this.across = 0;
        this.playerValue = 0;
    }

    public Field(int across, int down) {
        this.isAdjustable = false;
        this.down = down;
        this.across = across;
        this.playerValue = -1;
    }

    public int getDown() {
        if (this.isAdjustable) {
            Print("Warning - attempting to retrieve \"down\" value of an "
                    + "adjustable field object. Returning 0.");
        }
        return this.down;
    }

    public int getAcross() {
        if (this.isAdjustable) {
            Print("Warning - attempting to retrieve the \"across\" value of "
                    + "an adjustable field object. Returning 0.");
        }
        return this.across;
    }

    public boolean isAdjustable() {
        return this.isAdjustable;
    }

    public int getPlayerValue() {
        if (!this.isAdjustable) {
            Print("Warning - attempting to retrieve the player value of "
                    + "an non-adjustable field object. Returning -1.");
        }
        return this.playerValue;
    }

    public void setPlayerValue(int value) {
        if (this.isAdjustable) {
            if (value < 0) {
                System.out
                        .println("Error- cannot set the value to a negative number");
            } else {
                this.playerValue = value;
            }
        } else {
            Print("Warning - attempting to set the player value of a "
                    + "non-adjustable field object. player value is set "
                    + "to 0");
            this.playerValue = 0;
        }
    }

    public static void Print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        /*
         * test the features of this class
         */

        // create some field objects
        Field whiteField = new Field();
        Field blackField = new Field(3, 5);

        // test if the field recognizes if it is an adjustable (white)
        // or not adjustable (black) field.
        System.out.println(whiteField.isAdjustable());
        System.out.println(blackField.isAdjustable());

        System.out.println(whiteField.getDown());
        System.out.println(whiteField.getAcross());

        System.out.println(blackField.getDown());
        System.out.println(blackField.getAcross());

        System.out.println(whiteField.getPlayerValue());
        System.out.println(blackField.getPlayerValue());

        whiteField.setPlayerValue(3);
        System.out.println(whiteField.getPlayerValue());

        blackField.setPlayerValue(4);
        System.out.println(blackField.getPlayerValue());

        whiteField.setPlayerValue(-1);
    }
}
