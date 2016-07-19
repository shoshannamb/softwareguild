/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.unitconverter;

import com.sg.unitconverter.model.Conversion;
import com.sg.unitconverter.ops.ConverterOps;
import com.sg.unitconverter.ops.MassOps;
import com.sg.unitconverter.ops.TimeOps;
import com.sg.unitconverter.ops.VolumeOps;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author apprentice
 */
@Controller
public class UnitController {

    private ConverterOps ops;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getConversionModel(Model model) {
        model.addAttribute("conversion", new Conversion());
        return "index";
    }

    @RequestMapping(value = "convert", method = RequestMethod.POST)
    public String convertUnits(HttpServletRequest req, @Valid @ModelAttribute("conversion") Conversion toConvert, BindingResult result, Model model) {
        if (result.hasErrors()) {     // if validation fails
            return "index";
        }

        String unitType = req.getParameter("unitType").toLowerCase();

        switch (unitType) {
            case "time":
                ops = new TimeOps();
                break;
            case "volume":
                ops = new VolumeOps();
                break;
            case "mass":
                ops = new MassOps();
                break;
            default:
                return "inputError";
        }

        model.addAttribute("converted", ops.calculate(toConvert));
        model.addAttribute("conversion", new Conversion());
        return "unitResults";
    }
}
