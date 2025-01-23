package es.atgti.datavinci;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DNIFormat {
    @Builder.Default
    boolean withPoints = false;
    @Builder.Default
    boolean upperCase = true;
    @Builder.Default
    boolean zerosPadding = false;
    @Builder.Default
    Character separator = null;

    public static final DNIFormat DEFAULT = DNIFormat.builder().build();
}
