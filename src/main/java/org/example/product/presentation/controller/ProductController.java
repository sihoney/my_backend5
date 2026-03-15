package org.example.product.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.product.application.dto.CreateProductInput;
import org.example.product.application.dto.UpdateProductInput;
import org.example.product.domain.model.Product;
import org.example.product.presentation.dto.request.CreateProductRequest;
import org.example.product.presentation.dto.request.UpdateProductRequest;
import org.example.product.application.usecase.ProductUsecase;
import org.example.product.presentation.dto.response.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "상품 CRUD API")
//@RequiredArgsConstructor
public class ProductController {

    private final ProductUsecase productUsecase;

    public ProductController(ProductUsecase productUsecase) {
        this.productUsecase = productUsecase;
    }

    @PostMapping
    @Operation(summary = "상품 생성", description = "신규 상품을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "요청 값 오류")
    })
    public ResponseEntity<ProductResponse> create(
            @Parameter(description = "요청자 UUID")
            @RequestHeader("X-Actor-Id") UUID actorId,
            @RequestBody CreateProductRequest request
    ) {
        Product created = productUsecase.create(new CreateProductInput(
                request.sellerId(),
                request.name(),
                request.description(),
                request.price(),
                request.stock(),
                request.status(),
                actorId.toString()
        ));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductResponse.from(created));
    }

    @GetMapping("/{productId}")
    @Operation(summary = "상품 단건 조회", description = "상품 ID로 상품 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "상품 없음")
    })
    public ProductResponse getById(
            @Parameter(description = "상품 UUID") @PathVariable UUID productId
    ) {
        return ProductResponse.from(
                productUsecase.getById(productId)
        );
    }

    @GetMapping
    @Operation(summary = "상품 목록 조회", description = "전체 상품 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public List<ProductResponse> getAll() {
        return productUsecase.getAll().stream()
                .map(ProductResponse::from)
                .toList();
    }

    @PutMapping("/{productId}")
    @Operation(summary = "상품 수정", description = "상품 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "상품 없음")
    })
    public ProductResponse update(
            @Parameter(description = "상품 UUID") @PathVariable UUID productId,
            @Parameter(description = "요청자 UUID") @RequestHeader("X-Actor-Id") UUID actorId,
            @Valid @RequestBody UpdateProductRequest request
    ) {
        Product updated = productUsecase.update(productId, new UpdateProductInput(
                request.name(),
                request.description(),
                request.price(),
                request.stock(),
                request.status(),
                actorId.toString()
        ));
        return ProductResponse.from(updated);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "상품 없음")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "상품 UUID") @PathVariable UUID productId
    ) {
        productUsecase.delete(productId);
        return ResponseEntity.noContent().build();
    }
}
