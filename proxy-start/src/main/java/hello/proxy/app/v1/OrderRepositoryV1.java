package hello.proxy.app.v1;

import lombok.RequiredArgsConstructor;

public interface OrderRepositoryV1 {

    void save(String itemId);
}
