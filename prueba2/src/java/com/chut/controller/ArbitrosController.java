package com.chut.controller;

import com.chut.controller.util.MobilePageController;
import com.chut.entidades.Arbitros;
import com.chut.facade.ArbitrosFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "arbitrosController")
@ViewScoped
public class ArbitrosController extends AbstractController<Arbitros> {

    @Inject
    private CampeonatosController idcampeonatoController;
    @Inject
    private MobilePageController mobilePageController;

    public ArbitrosController() {
        // Inform the Abstract parent controller of the concrete Arbitros Entity
        super(Arbitros.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        idcampeonatoController.setSelected(null);
    }

    /**
     * Sets the "selected" attribute of the Campeonatos controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdcampeonato(ActionEvent event) {
        Arbitros selected = this.getSelected();
        if (selected != null && idcampeonatoController.getSelected() == null) {
            idcampeonatoController.setSelected(selected.getIdcampeonato());
        }
    }

}
