package com.chiragbohet.ecommerce.Repositories;

import com.chiragbohet.ecommerce.Utilities.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {

    ConfirmationToken findByConfirmationToken(String confirmationToken);

}
