package ru.mf.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import ru.mf.user.UserDto;

import java.util.List;


public class ClientForm extends FormLayout {
    TextField organisationName = new TextField("Organisation name");
    TextField inn = new TextField("Identification number");
    TextField tenant = new TextField("Tenant");
    TextField personalAccount = new TextField("Account number");
    TextField msisdn = new TextField("Msisdn");
    TextField vip = new TextField("Vip");
    ComboBox<UserDto> appUsers = new ComboBox<>("CCC");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public ClientForm(List<UserDto> userDtos) {
        addClassName("client-form");

        appUsers.setItems(userDtos);
        appUsers.setItemLabelGenerator(UserDto::getLastName);

        add(
                organisationName,
                inn,
                tenant,
                personalAccount,
                msisdn,
                vip,
                appUsers,
                createButtonLayout()
        );
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        delete.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

}
