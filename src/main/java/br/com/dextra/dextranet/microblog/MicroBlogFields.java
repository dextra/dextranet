package br.com.dextra.dextranet.microblog;

public enum MicroBlogFields {

    TEXTO("texto"), DATA("data"), ID("id"), AUTOR("autor"), USUARIOMD5("usuarioMD5");

    private String value;

    MicroBlogFields(String value) {
        this.value = value;
    }

    public String getField() {
        return value;
    }
}
