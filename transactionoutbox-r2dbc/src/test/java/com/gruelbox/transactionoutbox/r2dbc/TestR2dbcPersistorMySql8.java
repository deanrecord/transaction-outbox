package com.gruelbox.transactionoutbox.r2dbc;

import static com.gruelbox.transactionoutbox.r2dbc.UsesMySql8.connectionConfiguration;

import com.gruelbox.transactionoutbox.Persistor;
import com.gruelbox.transactionoutbox.r2dbc.R2dbcRawTransactionManager.ConnectionFactoryWrapper;
import com.gruelbox.transactionoutbox.spi.BaseTransactionManager;
import com.gruelbox.transactionoutbox.sql.AbstractPersistorTest;
import com.gruelbox.transactionoutbox.sql.Dialect;
import com.gruelbox.transactionoutbox.sql.Dialects;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.spi.Connection;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import reactor.core.publisher.Hooks;

@Slf4j
class TestR2dbcPersistorMySql8 extends AbstractPersistorTest<Connection, R2dbcRawTransaction>
    implements UsesMySql8 {

  private final R2dbcPersistor persistor = R2dbcPersistor.forDialect(Dialects.MY_SQL_8);
  private final ConnectionFactoryWrapper connectionFactory =
      R2dbcRawTransactionManager.wrapConnectionFactory(
          MySqlConnectionFactory.from(connectionConfiguration()));
  private final R2dbcRawTransactionManager txManager =
      new R2dbcRawTransactionManager(connectionFactory);

  @BeforeAll
  static void initHooks() {
    Hooks.onOperatorDebug();
  }

  @Override
  protected Dialect dialect() {
    return Dialects.MY_SQL_8;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Persistor<Connection, R2dbcRawTransaction> persistor() {
    return (Persistor) persistor;
  }

  @Override
  protected BaseTransactionManager<Connection, R2dbcRawTransaction> txManager() {
    return txManager;
  }
}
