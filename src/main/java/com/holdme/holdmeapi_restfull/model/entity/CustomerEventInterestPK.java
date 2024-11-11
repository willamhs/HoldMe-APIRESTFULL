package com.holdme.holdmeapi_restfull.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
@Data
@Embeddable
@EqualsAndHashCode
public class CustomerEventInterestPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "id_customer_interest", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_customer_interest_id"))
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "id_event_interest", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_event_Interest_id"))
    private Event event;
}
