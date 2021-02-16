package com.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myapp.web.rest.TestUtil;

public class CustAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustAccount.class);
        CustAccount custAccount1 = new CustAccount();
        custAccount1.setId(1L);
        CustAccount custAccount2 = new CustAccount();
        custAccount2.setId(custAccount1.getId());
        assertThat(custAccount1).isEqualTo(custAccount2);
        custAccount2.setId(2L);
        assertThat(custAccount1).isNotEqualTo(custAccount2);
        custAccount1.setId(null);
        assertThat(custAccount1).isNotEqualTo(custAccount2);
    }
}
