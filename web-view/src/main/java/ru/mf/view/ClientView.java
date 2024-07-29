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

import ru.mf.user.UserDto;
import ru.mf.user.service.UserService;

import java.util.Collections;



@PageTitle("C3po | Users")
@Route("/admin/users")
public class UserView extends VerticalLayout {
    private final UserService userService;

    Grid<UserDto> grid = new Grid<>(UserDto.class);
    TextField filterText = new TextField();
    ClientForm clientForm;


    @Autowired
    public UserView(UserService userService) {
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        this.userService = userService;
        grid.setItems(userService.findAllUsers());

        add(
                getToolbar(),
                getContent()
        );
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
        clientForm = new ClientForm(Collections.emptyList());
        clientForm.setWidth("25em");
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addContactButton = new Button("Add user");

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureGrid() {
        grid.addClassName("user-grid");
        grid.setSizeFull();
        grid.setColumns("id", "firstName", "lastName", "email");
        grid.getColumns().forEach(c -> c.setAutoWidth(true));
    }

}
