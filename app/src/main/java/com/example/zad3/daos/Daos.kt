package com.example.zad3.daos


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update
import com.example.zad3.models.Product
import com.example.zad3.models.ShoppingList
import kotlinx.coroutines.flow.Flow
import javax.sql.DataSource

@Dao
interface ProductDao {

@Insert
suspend fun insert(prodcut: Product)



    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(prodcut: Product)

    @Delete
    suspend fun delete(prodcut: Product)

    @Query("SELECT * FROM products")
    fun getAllItems(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE productId =:productId")
    fun getItem(productId: Int): Flow<Product>

    @Query("""select * from products where shoppingListId=:id""")
     fun   getProductListById(id: Long): Flow<List<Product>>



     @Query(""" delete from products where shoppingListId=:id """)
    suspend fun deleteAllProductsById(id: Long)

    @Query(""" update products set isChecked=:bool where productId=:id""")
    fun setCheck(bool: Boolean,id:Long)
    @Query(""" select* from products where productId=:productId""")
    fun getProduct(productId: Long): Flow<Product>


    @Query(""" update products set isChecked =:checked where productId=:id """)
   fun queryUpdate(id:Long, checked: Boolean)

}



@Dao
interface ShoppingListDao {



    @Insert
    fun insertProductList(productList: List<Product>)

    @Query("SELECT last_insert_rowid()")
   suspend fun getLastInsertedId(): Int


    @Delete
    suspend fun delete(shoppingList: ShoppingList)

    @Query("""SELECT * FROM shoppingLists AS SL INNER JOIN products AS P
        ON SL.id = P.shoppingListId where SL.id=:id""")
    fun getShoppingListWithProductsFilteredById(id: Int): Flow<ShoppingListWithProducts>


    @Transaction
    @Query("SELECT * FROM shoppingLists")
    fun getShoppingListsWithProductsEmbedded(): Flow<List<ShoppingListWithProducts>>


    @Insert
    suspend fun insertShoppingListWithProducts(shoppingList: ShoppingList)

    @Update
    suspend fun update(shoppingList: ShoppingList)

    @Query("UPDATE products SET shoppingListId = :id WHERE shoppingListId = 0")
     fun getAllProductsByShoppingListId(id:Int)



    @Insert
    suspend fun insert(shoppingList: ShoppingList): Long

    @Query(""" Select * from shoppingLists""")
  fun getShoppingListSet():Flow<List<ShoppingList>>


  @Query("""SELECT * FROM shoppingLists AS SL INNER JOIN products AS P ON SL.id = P.shoppingListId""")
    fun getShoppingListsWithProducts(): Flow<List<ShoppingListWithProducts>>

    @Query(""" Select * from SHOPPINGLISTS where id = :id   """)
    fun getShoppingListById(id: Long): Flow<ShoppingList>

    @Query(""" delete from products""")
    suspend fun delete()


  }

data class ShoppingListWithProducts(

    @Embedded val shoppingList: ShoppingList,
    @Relation(
        parentColumn = "id",
        entityColumn = "shoppingListId"
    )
    val productList: List<Product> = emptyList()
)