# redis-helper

*根据配置自动生成对应的缓存操作。*


参考数据库索引设计，目前定义了两个缓存类型：
* ClusterIndex
    聚簇索引，该索引下挂的为实际缓存值
* Indexed
    普通索引，该索引下挂的为关联值，需要根据关联值进行"回表"到聚簇索引取到实际缓存值


支持配置多个聚簇索引，普通索引必须关联一个聚簇索引

## demo

### 配置:

```java
@BufferEntity(keyPrefix = "bankCard")
@Data
public class BankCard {

    @ClusterIndex
    private Long id;

    @ClusterIndex
    private String indexCardId;

    @Indexed(ref = "id")
    private String cardNo;

    private String bankName;

    private String cardType;

    private String name;

    @Indexed(ref ="indexCardId")
    private String mobile;
}
```
bankCard中配置了两个聚簇索引，两个普通索引。

根据上面的配置的话，缓存中的结构应该是：
```

bankCard:id:{id}                      ->  {bankCard_OBJECT}
bankCard:indexCardId:{indexCardId}    ->  {bankCard_OBJECT}

bankCard:cardNo:{cardNo}              ->  {bankCard.id}
bankCard:mobile:{mobile}              ->  {bankCard.indexCardId}

```

### 使用

<b>入参仅聚簇索引</b>

如果参数值和`bufferEntity`中的缓存key名字一样的话可以省略`@Filed`注解
```java
    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int delById(String id) {
        //code
    }
```
根据配置 生成的需要删除的缓存key为：
```
->bankCard:id:a
->bankCard:indexCardId:(bankCard:id:{id}).indexCardId
->bankCard:mobile:(bankCard:id:{id}).mobile
->bankCard:cardNo:(bankCard:id:{id}).cardNo
```

<b>入参两个普通索引</b>
```java
    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int delByCardNoAndMobile(@Field("cardNo") String cardNo, String mobile){
        //code
    }
```
生成的缓存key为:
```
->bankCard:id:(bankCard:cardNo:{cardNo})
->bankCard:indexCardId:(bankCard:mobile:{mobile})
```
<b>入参一个普通索引，一个没有索引</b>
```java
    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int delByCardNoAndType(@Field("cardNo") String cardNo,@Field("cardType") String type){
        //code
    }
```

对应的缓存key为：
```
->bankCard:id:(bankCard:cardNo:{cardNo})
->bankCard:indexCardId:(bankCard:id:(bankCard:cardNo:{cardNo})).indexCardId
->bankCard:mobile:(bankCard:id:(bankCard:cardNo:{cardNo})).mobile
```

关于update 和 put 操作还没想好怎么实现。

大概分几种情况吧：
### update
1 更新的时候dal层传入整个DO对象，对象里面所有值都有值，用where指定更新范围。
    
    *这种方式应该是用起来最难受的，但是切面实现起来简单*
2 只传要更新的值，用where指定更新范围。
    
    *这样怎么去更新缓存是个问题，不能保证where中关联的key在缓存中一定有值，可以没值的话直接降级为删除缓存？*
### put
1 put只能传入完整的DO对象。    