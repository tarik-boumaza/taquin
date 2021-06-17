package sample;

public class Message {

    /**
     * Identifiant de l'expéditeur.
     */
    private int expediteur;

    /**
     * Identifiant du destinataire.
     */
    private int destinataire;

    /**
     * Case à libérer.
     */
    private int caseLibere;

    /**
     * Constructeur par défaut.
     */
    public Message() {
    }

    /**
     * Constructeur avec paramètre.
     * @param expediteur Identifiant de l'expéditeur.
     * @param destinataire Identifiant du destinataire.
     * @param caseLibere Case à libérer.
     */
    public Message(int expediteur, int destinataire, int caseLibere) {
        this.expediteur = expediteur;
        this.destinataire = destinataire;
        this.caseLibere = caseLibere;
    }
}
