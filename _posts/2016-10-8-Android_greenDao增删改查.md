---
layout: post
title: Android_greenDao增删改查
---

greenDao是一个使用于android的ORM框架,现在主流的ORM框架有OrmLite,SugarORM,Active Android,Realm以及GreenDAO.greenDao的性能远远高于同类的ORM框架,具体的测试结果官网有。


示例代码：[https://github.com/7449/AndroidDevelop/tree/master/greenDao](https://github.com/7449/AndroidDevelop/tree/master/greenDao)

用greenDao实现了数据库的增删改查,确实比以前自己写SQL语句舒服多了,不用再考虑SQL语句很方便。



IDE工具：AndroidStudio

gradle引用：

    compile 'org.greenrobot:greendao-generator:2.2.0'
    compile 'org.greenrobot:greendao:2.2.0'

单独创建一个目录，起名为sql,然后创建一个GreenDaoGenerator,注意一点是这里就要运行一下这个java类，不是app是这个java类，需要单独运行一下生成sql代码

	public class MyGreenDaoGenerator {

	    public static void main(String[] args) throws Exception {
	        //版本号,包名
	        Schema schema = new Schema(1, "github.com.greendao.sql");
	        Entity user = schema.addEntity("User");
	        user.addIdProperty().primaryKey();
	        user.addStringProperty("userName").notNull();
	        user.addStringProperty("userSex").notNull();
	        new DaoGenerator().generateAll(schema, "./app/src/main/java");
	    }
	
	}

最后会自动生成这几个文件

![_config.yml]({{ site.baseurl }}/images/greendao.png)


主要就是增删改查之类的方法


	public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	
	    private EditText userName;
	    private EditText userSex;
	    private EditText etDelete;
	    private EditText etUpDateId;
	    private EditText etUpDateName;
	    private EditText etUpDateSex;
	    private EditText etSearch;
	
	
	    private SQLiteDatabase writableDatabase;
	    private UserDao userDao;
	    private ListView listView;
	    private SimpleCursorAdapter simpleCursorAdapter;
	
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	
	        userName = (EditText) findViewById(R.id.userName);
	        userSex = (EditText) findViewById(R.id.userSex);
	        listView = (ListView) findViewById(R.id.list);
	        etDelete = (EditText) findViewById(R.id.et_delete);
	        etUpDateId = (EditText) findViewById(R.id.et_update_id);
	        etUpDateName = (EditText) findViewById(R.id.et_update_name);
	        etUpDateSex = (EditText) findViewById(R.id.et_update_sex);
	        etSearch = (EditText) findViewById(R.id.et_search);
	
	        findViewById(R.id.add).setOnClickListener(this);
	        findViewById(R.id.delete).setOnClickListener(this);
	        findViewById(R.id.update).setOnClickListener(this);
	        findViewById(R.id.search).setOnClickListener(this);
	
	        init();
	    }
	
	    private void init() {
	        writableDatabase = new DaoMaster.DevOpenHelper(this, "greendao", null).getWritableDatabase();
	        DaoSession daoSession = new DaoMaster(writableDatabase).newSession();
	
	        //得到Dao的对象
	        userDao = daoSession.getUserDao();
	        // 遍历表中所有的数据
	
	        Cursor cursor = writableDatabase.query(userDao.getTablename(), userDao.getAllColumns(), null, null, null, null, null);
	        String[] from = {UserDao.Properties.UserName.columnName, UserDao.Properties.UserSex.columnName};
	        int[] id = {android.R.id.text1, android.R.id.text2};
	        simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, id, Adapter.NO_SELECTION);
	        listView.setAdapter(simpleCursorAdapter);
	    }
	
	    @Override
	    public void onClick(View v) {
	        switch (v.getId()) {
	            case R.id.add:
	                if (!userName.getText().toString().isEmpty() && !userSex.getText().toString().isEmpty()) {
	                    addSQLite(userName.getText().toString(), userSex.getText().toString());
	                    userName.getText().clear();
	                    userSex.getText().clear();
	                } else {
	                    Toast.makeText(getApplicationContext(), "name sex must not null", Toast.LENGTH_LONG).show();
	                }
	                break;
	
	            case R.id.delete:
	                if (!etDelete.getText().toString().isEmpty()) {
	                    deleteSQLite(Long.valueOf(etDelete.getText().toString().trim()));
	                    etDelete.getText().clear();
	                } else {
	                    Toast.makeText(getApplicationContext(), "id illegal", Toast.LENGTH_LONG).show();
	                }
	                break;
	
	            case R.id.update:
	                if (!etUpDateId.getText().toString().isEmpty() && !etUpDateName.getText().toString().isEmpty() && !etUpDateSex.getText().toString().isEmpty()) {
	                    upDateSQLite(Long.parseLong(etUpDateId.getText().toString().trim()), etUpDateName.getText().toString().trim(), etUpDateSex.getText().toString().trim());
	                    etUpDateId.getText().clear();
	                    etUpDateName.getText().clear();
	                    etUpDateSex.getText().clear();
	                } else {
	                    Toast.makeText(getApplicationContext(), "id name sex must not null", Toast.LENGTH_LONG).show();
	                }
	                break;
	
	            case R.id.search:
	                if (!etSearch.getText().toString().isEmpty()) {
	                    searchSQLite(Long.parseLong(etSearch.getText().toString().trim()));
	                    etSearch.getText().clear();
	                } else {
	                    Toast.makeText(getApplicationContext(), "id illegal", Toast.LENGTH_LONG).show();
	                }
	                break;
	
	        }
	        Cursor cursor = writableDatabase.query(userDao.getTablename(), userDao.getAllColumns(), null, null, null, null, null);
	        simpleCursorAdapter.swapCursor(cursor);
	    }
	
	    //add sql data
	    private void addSQLite(String name, String sex) {
	
	        User user = new User(null, name, sex);
	
	        userDao.insert(user);
	    }
	
	
	    //delete sql data
	    private void deleteSQLite(Long id) {
	
	        userDao.deleteByKey(id);
	        //delete sql all;
	//        userDao.deleteAll();
	
	    }
	
	
	    //update sql data
	    private void upDateSQLite(long id, String name, String sex) {
	        userDao.update(new User(id, name, sex));
	    }
	
	    //search sql data
	    private void searchSQLite(long id) {
	        QueryBuilder<User> queryBuilder = userDao.queryBuilder().where(UserDao.Properties.Id.eq(id));
	        // .list() Returns a collection of entity classes
	        List<User> user = queryBuilder.list();
	        // If you only want results , use .unique() method
	        // Person person = queryBuilder.unique();
	        new AlertDialog
	                .Builder(this)
	                .setMessage(user != null && user.size() > 0 ? user.get(0).getUserName() + "--" + user.get(0).getUserSex() : "data null")
	                .setPositiveButton("ok", null).create().show();
	    }
	}



![_config.yml]({{ site.baseurl }}/images/greendaoImage.gif)

