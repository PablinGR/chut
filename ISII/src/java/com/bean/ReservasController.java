package com.bean;

import MD.Reservas;
import com.session.ReservasFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "reservasController")
@ViewScoped
public class ReservasController extends AbstractController<Reservas> {

    @Inject
    private JugadoresController cedulajugadorController;
    @Inject
    private PartidosController idpartidoController;

    // Flags to indicate if child collections are empty
    private boolean isCampeonatosCollectionEmpty;
    private boolean isHorariosCollectionEmpty;

    public ReservasController() {
        // Inform the Abstract parent controller of the concrete Reservas Entity
        super(Reservas.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        cedulajugadorController.setSelected(null);
        idpartidoController.setSelected(null);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsCampeonatosCollectionEmpty();
        this.setIsHorariosCollectionEmpty();
    }

    public boolean getIsCampeonatosCollectionEmpty() {
        return this.isCampeonatosCollectionEmpty;
    }

    private void setIsCampeonatosCollectionEmpty() {
        Reservas selected = this.getSelected();
        if (selected != null) {
            ReservasFacade ejbFacade = (ReservasFacade) this.getFacade();
            this.isCampeonatosCollectionEmpty = ejbFacade.isCampeonatosCollectionEmpty(selected);
        } else {
            this.isCampeonatosCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Campeonatos entities that
     * are retrieved from Reservas?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Campeonatos page
     */
    public String navigateCampeonatosCollection() {
        Reservas selected = this.getSelected();
        if (selected != null) {
            ReservasFacade ejbFacade = (ReservasFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Campeonatos_items", ejbFacade.findCampeonatosCollection(selected));
        }
        return "/app/campeonatos/index";
    }

    /**
     * Sets the "selected" attribute of the Jugadores controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareCedulajugador(ActionEvent event) {
        Reservas selected = this.getSelected();
        if (selected != null && cedulajugadorController.getSelected() == null) {
            cedulajugadorController.setSelected(selected.getCedulajugador());
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

    public boolean getIsHorariosCollectionEmpty() {
        return this.isHorariosCollectionEmpty;
    }

    private void setIsHorariosCollectionEmpty() {
        Reservas selected = this.getSelected();
        if (selected != null) {
            ReservasFacade ejbFacade = (ReservasFacade) this.getFacade();
            this.isHorariosCollectionEmpty = ejbFacade.isHorariosCollectionEmpty(selected);
        } else {
            this.isHorariosCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Horarios entities that
     * are retrieved from Reservas?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Horarios page
     */
    public String navigateHorariosCollection() {
        Reservas selected = this.getSelected();
        if (selected != null) {
            ReservasFacade ejbFacade = (ReservasFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Horarios_items", ejbFacade.findHorariosCollection(selected));
        }
        return "/app/horarios/index";
    }

}
