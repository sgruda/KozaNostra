package pl.lodz.p.it.ssbd2020.ssbd05;

import lombok.Getter;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
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
        for(int i=0; i<15; i++) {
            images.add("picture" + i + ".jpg");
        }
    }
}
