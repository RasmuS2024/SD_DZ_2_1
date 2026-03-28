package tiger.bankapp.controller;

import tiger.bankapp.facade.*;

/**
 * Группирует все фасады для упрощения передачи зависимостей в обработчики
 */
public record FacadeContext(
        AccountFacade accountFacade,
        CategoryFacade categoryFacade,
        OperationFacade operationFacade,
        AnalyticsFacade analyticsFacade
) {}