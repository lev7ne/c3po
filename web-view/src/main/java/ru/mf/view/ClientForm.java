package ru.mf.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import ru.mf.client.ClientViewDto;
import ru.mf.user.UserDto;

import java.util.List;


public class ClientForm extends FormLayout {
    Binder<ClientViewDto> binder = new BeanValidationBinder<>(ClientViewDto.class);

    TextField orgName = new TextField("Organisation name");
    TextField inn = new TextField("Identification number");
    TextField tenant = new TextField("Tenant");
    TextField personalAccount = new TextField("Account number");
    TextField msisdn = new TextField("Msisdn");
    ComboBox<UserDto> appUsers = new ComboBox<>("CCC");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    private ClientViewDto dto;


    public ClientForm(List<UserDto> userDtos) {
        addClassName("client-form");
        binder.bindInstanceFields(this);

        appUsers.setItems(userDtos);
        appUsers.setItemLabelGenerator(UserDto::getLastName);

        binder.forField(appUsers)
                .bind(ClientViewDto::getAppUser, ClientViewDto::setAppUser);

        add(
                orgName,
                inn,
                tenant,
                personalAccount,
                msisdn,
                appUsers,
                createButtonLayout()
        );
    }

    public void setClient(ClientViewDto dto) {
        this.dto = dto;
        binder.readBean(dto);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, dto)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        delete.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(dto);
            if (appUsers.getValue() != null) {
                dto.setAppUser(appUsers.getValue());
            }
            fireEvent(new SaveEvent(this, dto));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ClientFormEvent extends ComponentEvent<ClientForm> {
        private ClientViewDto dto;

        protected ClientFormEvent(ClientForm source, ClientViewDto dto) {
            super(source, false);
            this.dto = dto;
        }

        public ClientViewDto getClient() {
            return dto;
        }
    }

    public static class SaveEvent extends ClientFormEvent {
        SaveEvent(ClientForm source, ClientViewDto dto) {
            super(source, dto);
        }
    }

    public static class DeleteEvent extends ClientFormEvent {
        DeleteEvent(ClientForm source, ClientViewDto dto) {
            super(source, dto);
        }
    }

    public static class CloseEvent extends ClientFormEvent {
        CloseEvent(ClientForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
