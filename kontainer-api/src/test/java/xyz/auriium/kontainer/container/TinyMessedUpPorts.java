package xyz.auriium.kontainer.container;

import com.github.dockerjava.api.model.PortBinding;
import xyz.auriium.kontainer.utils.Optionals;

import java.util.Optional;

public class TinyMessedUpPorts extends TinyImageTerms{
    public TinyMessedUpPorts(String name) {
        super(name);
    }

    @Override
    public Optional<PortBinding[]> getPortBindings() {
        return Optionals.supply(PortBinding.parse("55:55"), PortBinding.parse("55:55"));
    }
}
