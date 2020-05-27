package com.chiragbohet.ecommerce.co;

import com.chiragbohet.ecommerce.services.ViewService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
public class ViewControllers {

    @Autowired
    ViewService viewService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/home")
    public ModelAndView viewAdminHome(HttpServletRequest request) {
        return viewService.viewAdminHome(request);
    }

}
