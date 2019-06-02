namespace java codeTest.v1.netty2.ssy07thrift.entity
typedef i16 short
typedef i32 int
typeof i64 long

struct Person{
	1: optional String username,
	2: optional int age,
	3: optional boolean married
}
exception DataException{
	1: optional String message,
	2: optional String callStack,
	3: optional String date
}
service PersonService{
	Person getPersonByUsername(1: required String username) throws(1: DataException dataException)
	void savePerson(1: required Person person) throws (1: DataException dataException)
}

























