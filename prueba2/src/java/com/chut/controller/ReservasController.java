package com.chut.controller;

import com.chut.controller.util.MobilePageController;
import com.chut.entidades.Reservas;
import com.chut.facade.ReservasFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "reservasController")
@ViewScoped
public class ReservasController extends AbstractController<Reservas> {

    @Inject
    private CampeonatosController idcampeonatoController;
    @Inject
    private HorariosController idhorarioController;
    @Inject
    private PartidosController idpartidoController;
    @Inject
    private MobilePageController mobilePageController;

    public ReservasController() {
        // Inform the Abstract parent controller of the concrete Reservas Entity
        super(Reservas.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        idcampeonatoController.setSelected(null);
        idhorarioController.setSelected(null);
        idpartidoController.setSelected(null);
    }

    /**
     * Sets the "selected" attribute of the Campeonatos controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdcampeonato(ActionEvent event) {
        Reservas selected = this.getSelected();
        if (selected != null && idcampeonatoController.getSelected() == null) {
            idcampeonatoController.setSelected(selected.getIdcampeonato());
        }
    }

    /**
     * Sets the "selected" attribute of the Horarios controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdhorario(ActionEvent event) {
        Reservas selected = this.getSelected();
        if (selected != null && idhorarioController.getSelected() == null) {
            idhorarioController.setSelected(selected.getIdhorario());
        }
    }

    /**
     * Sets the "selected" attribute of the Partidos controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdpartido(ActionEvent event) {
        Reservas selected = this.getSelected();
        if (selected != null && idpartidoController.getSelected() == null) {
            idpartidoController.setSelected(selected.getIdpartido());
        }
    }

}
