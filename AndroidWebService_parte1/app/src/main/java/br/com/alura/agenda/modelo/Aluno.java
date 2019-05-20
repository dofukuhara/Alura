package br.com.alura.agenda.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/*
    No obj Aluno contido na lista que o servidor nos retorna, existem 2 itens que não estavam inicialmente
    configurados no nosso objeto. São os atributos "desativado" e "idCliente".
    Como esses itens não foram configurados, o Jackson quebra a nossa aplicação, pois ele não sabe como lidar
    com esses atributos.
    Uma solução é utilizar a annotation @JsonIgnoreProperties(ignoreUnknown = true). Dessa forma, os atributos
    que estiverem no Json mas não configurados em nosso objeto serão descartados.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Aluno implements Serializable {

    private String id;
    private String nome;
    private String endereco;
    private String telefone;
    private String site;
    private Double nota;
    private String caminhoFoto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    @Override
    public String toString() {
        return getId() + " - " + getNome();
    }
}
