package com.singham.yuan.ws.test.server.repository;

import com.singham.yuan.ws.test.server.model.Student;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface StudentRepository {

    List<Student> query();

    void save(Student student);

}
