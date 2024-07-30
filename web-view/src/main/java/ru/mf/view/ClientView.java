package ru.mf.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mf.client.ClientDto;
import ru.mf.client.service.ClientService;
import ru.mf.user.service.UserService;


@PageTitle("C3po | Clients")
@Route("/clients")
public class ClientView extends VerticalLayout {
    private final ClientService clientService;
    private final UserService userService;

    Grid<ClientDto> grid = new Grid<>(ClientDto.class);
    TextField filterText = new TextField();
    ClientForm clientForm;

    @Autowired
    public ClientView(ClientService clientService, UserService userService) {
        this.userService = userService;
        this.clientService = clientService;

        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        grid.setItems(clientService.findAllClients());

        add(
                getToolbar(),
                getContent()
        );

        updateList();
    }

    private void updateList() {
        grid.setItems(clientService.findAllClients(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, clientForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, clientForm);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        clientForm = new ClientForm(userService.findAllUsers());
        clientForm.setWidth("25em");
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add client");

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureGrid() {
        grid.addClassName("clients-grid");
        grid.setSizeFull();
        grid.setColumns("id", "orgName", "inn", "tenant", "personalAccount", "msisdn", "vip", "appUser", "createdDate");
        grid.getColumns().forEach(c -> c.setAutoWidth(true));
    }

}
