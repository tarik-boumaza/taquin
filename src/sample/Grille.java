package sample;

public class Grille {

    final private int taille;
    final private int nb_agent;
    private final Agent[] agents;
    private final int[] grille;

    public Grille(final int taille, final int nb_agent) {
        this.taille = taille;
        this.nb_agent = nb_agent;
        this.grille = new int[taille*taille];
        this.agents = new Agent[nb_agent];
        initPositions();
    }

    public Grille(final int taille, final Agent[] agents) {
        this.taille = taille;
        this.nb_agent = agents.length;
        this.grille = new int[taille*taille];
        this.agents = agents;


    }

    public void initExemple() {
        for(int i=0; i<taille*taille; i++){
            for(int j=0; j<nb_agent; j++){
                if(i == agents[j].getPosition()){
                    grille[i] = agents[j].getPositionFinale();
                }
            }
        }
    }

    private void initPositions() {
        int random;
        int[] tab_id = new int[nb_agent];
        int[] tab_pos = new int[taille*taille];

        for (int i = 0; i < nb_agent; i++) {
            tab_id[i] = i + 1;
        }
        for (int i = 0; i < taille*taille; i++) {
            tab_pos[i] = 0;
        }

        for (int i = 0; i < nb_agent; i++) {
            random = (int)(Math.random()*(nb_agent-i-1));
            tab_pos[i] = tab_id[random];
            tab_id[random] = tab_id[nb_agent-i-1];

        }

        for (int i = 0; i < taille*taille; i++) {
            random = (int)(Math.random()*(taille*taille-i-1));
            grille[i] = tab_pos[random];
            if(tab_pos[random] > 0) {
                agents[random] = new Agent(tab_pos[random] - 1, i, this);
            }
            tab_pos[random] = tab_pos[(taille*taille)-i-1];
        }

    }

    public int getPosGrille(final int pos){
        return grille[pos];
    }

    public void setPosGrille(final int id, final int pos){
        grille[id] = pos;
    }

    public int getTaille() {
        return taille;
    }

    @Override
    public String toString() {
        synchronized (this){
            String str = "";
            for (int i = 0; i < taille; i++) {
                str += "---";
            }
            for (int i = 0; i < taille*taille; i++) {
                if (i % taille == 0) {
                    str += "\n";
                }
                if (grille[i] < 10) {
                    str += grille[i] + "  ";
                }
                else if (grille[i] > 0){
                    str += grille[i] + " ";
                }
                else {
                    str += "  ";
                }
            }
            str += "\n";
            for (int i = 0; i < taille; i++) {
                str += "---";
            }
            return str;
        }
    }

}
