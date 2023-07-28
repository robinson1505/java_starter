package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;

@Repository("fakeDao")
public class FakePersonAccessDataService implements PersonDao {
    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
       return DB;
    }

     @Override
    public Optional<Person> selectPerson(UUID id) {
      return DB.stream().filter(person -> person.getId().equals(id)).findFirst();
    }
    @Override
    public int deletePerson(UUID id) {
        Optional<Person> personMayBe = selectPerson(id);
        if(personMayBe.isEmpty()){
            return 0;
        }
        DB.remove(personMayBe.get());
        return 1;
    }

    @Override
    public int updatePerson(UUID id, Person update) {
       return selectPerson(id)
                      .map(person ->{
                        int personIndex = DB.indexOf(person);
                        if(personIndex >= 0){
                            DB.set(personIndex, new Person( id,update.getName()));
                            return 1;
                        }
                      return 0;
                      }).orElse(0);
    }

   

}
