package sample;

public class Message {

    /**
     * Identifiant de l'expéditeur.
     */
    private long expediteur;

    /**
     * Identifiant du destinataire.
     */
    private long destinataire;

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
    public Message(long expediteur, long destinataire, int caseLibere) {
        this.expediteur = expediteur;
        this.destinataire = destinataire;
        this.caseLibere = caseLibere;
    }

    public long getExpediteur() {
        return expediteur;
    }

    public long getDestinataire() {
        return destinataire;
    }

    public int getCaseLibere() {
        return caseLibere;
    }
}
