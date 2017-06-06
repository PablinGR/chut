package com.chut.controller;

import com.chut.controller.util.MobilePageController;
import com.chut.entidades.Jugadores;
import com.chut.facade.JugadoresFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "jugadoresController")
@ViewScoped
public class JugadoresController extends AbstractController<Jugadores> {

    @Inject
    private EquiposController idequipoController;
    @Inject
    private MobilePageController mobilePageController;

    public JugadoresController() {
        // Inform the Abstract parent controller of the concrete Jugadores Entity
        super(Jugadores.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        idequipoController.setSelected(null);
    }

    /**
     * Sets the "selected" attribute of the Equipos controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdequipo(ActionEvent event) {
        Jugadores selected = this.getSelected();
        if (selected != null && idequipoController.getSelected() == null) {
            idequipoController.setSelected(selected.getIdequipo());
        }
    }

}
