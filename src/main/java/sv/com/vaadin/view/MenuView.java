package sv.com.vaadin.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

import ru.xpoft.vaadin.VaadinView;
import javax.annotation.PostConstruct;


@SuppressWarnings("serial")
@Component
@Scope("prototype")
@VaadinView(value = MenuView.NAME, cached = true)
public class MenuView extends VerticalLayout implements View {

	public static final String NAME = "";
	private Label usernameLabel = new Label();
    private Label rolesLabel = new Label();

    @PostConstruct
    public void PostConstruct()
    {
    	setSizeFull();

    	HorizontalLayout usernameLayout = new HorizontalLayout();
        usernameLayout.setSpacing(true);
        usernameLayout.addComponent(new Label("Username:"));
        usernameLayout.addComponent(usernameLabel);

        HorizontalLayout userRolesLayout = new HorizontalLayout();
        userRolesLayout.setSpacing(true);
        userRolesLayout.addComponent(new Label("Roles:"));
        userRolesLayout.addComponent(rolesLabel);

        Button button = new Button("Provincia",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	Page.getCurrent().setUriFragment("!" + "Prueba");
            }
        });
        addComponent(usernameLayout);
        addComponent(userRolesLayout);
        addComponent(button);
        setComponentAlignment(button, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    	String username = "NULL";
        String roles = "NULL";

        if (SecurityContextHolder.getContext().getAuthentication() != null)
        {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<String> rolesList = new ArrayList<String>();
            for (GrantedAuthority grantedAuthority : user.getAuthorities())
            {
                rolesList.add(grantedAuthority.getAuthority());
            }

            username = user.getUsername();
            roles = StringUtils.join(rolesList, ",");
        }

        usernameLabel.setValue(username);
        rolesLabel.setValue(roles);
        Notification.show("Vaadin Prueba");
    }

}
