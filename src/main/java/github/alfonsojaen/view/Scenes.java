package github.alfonsojaen.view;

public enum Scenes {
    USERLOGIN("view/pantallaLoginUser.fxml"),
    USERREGISTER("view/pantallaRegisterUser.fxml"),
    INSERTCUADRILLA("view/pantallaInsertCuadrilla.fxml"),
    INSERTCOSTALERO("view/pantallaInsertCostalero.fxml"),
    EDITCUADRILLA("view/pantallaEditCuadrilla.fxml");

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }
}
