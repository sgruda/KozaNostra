package pl.lodz.p.it.ssbd2020.ssbd05.web;

import lombok.Getter;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Named(value = "imagesController")
@RequestScoped
public class ImagesController {

    @Getter
    private List<String> images;

    @PostConstruct
    public void init() {
        images = new ArrayList<>();
        String fullPath = ImagesController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String cutPath = fullPath.substring(0, fullPath.indexOf("target"));
        File[] files = new File(cutPath + "src/main/webapp/pictures").listFiles();
        assert files != null;
        for(File f : files) {
            images.add(f.getName());
        }
    }
}
