package com.example.divinkas.contactstest.Data;

public class ContactItem {
    private String id;
    private String first_name;
    private String last_name;
    private String link_pdf;
    private String posada;
    private String misce_rob;

    private String comment;

    public ContactItem(){ }

    public ContactItem(String id, String first_name, String last_name, String link_pdf, String posada, String misce_rob){
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.link_pdf = link_pdf;
        this.misce_rob = misce_rob;
        this.posada = posada;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getFirst_name() { return first_name; }

    public void setFirst_name(String first_name) { this.first_name = first_name; }

    public String getLast_name() { return last_name; }

    public void setLast_name(String last_name) { this.last_name = last_name; }

    public String getLink_pdf() { return link_pdf; }

    public void setLink_pdf(String link_pdf) { this.link_pdf = link_pdf; }

    public String getPosada() { return posada; }

    public void setPosada(String posada) { this.posada = posada; }

    public String getMisce_rob() { return misce_rob; }

    public void setMisce_rob(String misce_rob) { this.misce_rob = misce_rob; }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
