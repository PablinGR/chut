package com.bean;

import MD.Horarios;
import com.session.HorariosFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "horariosController")
@ViewScoped
public class HorariosController extends AbstractController<Horarios> {

    @Inject
    private ReservasController idreservaController;

    public HorariosController() {
        // Inform the Abstract parent controller of the concrete Horarios Entity
        super(Horarios.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        idreservaController.setSelected(null);
    }

    /**
     * Sets the "selected" attribute of the Reservas controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdreserva(ActionEvent event) {
        Horarios selected = this.getSelected();
        if (selected != null && idreservaController.getSelected() == null) {
            idreservaController.setSelected(selected.getIdreserva());
        }
    }

}
