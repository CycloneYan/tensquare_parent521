package com.tensquare.base.service;

import com.tensquare.base.dao.LableDao;
import com.tensquare.base.pojo.Lable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {
    @Autowired
    private LableDao labelDao;
    @Autowired
    private IdWorker idWorker;

    public List<Lable> findAll(){
        return labelDao.findAll();
    }
    public Lable findById(String id){
        return labelDao.findById(id).get();
    }

    public void save(Lable label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public void update(Lable label){
        labelDao.save(label);
    }

    public void deleteById(String id){
        labelDao.deleteById(id);
    }
    public List<Lable> findSerach(Lable label) {
        return labelDao.findAll(new Specification<Lable>() {
            /**
             *
             * @param root 根对象，也就是要把条件封装到哪个对象中，where类名=label.getid
             * @param query 封装的都是查询关键字，比如group by order by等
             * @param cb 用来封装条件对象的，如果直接返回null,表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Lable> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list=new ArrayList<>();
                if(label.getLabelname()!=null&&!"".equals(label.getLabelname())){
                    Predicate predicate=cb.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%");//where labelname like
                    list.add(predicate);
                }
                if(label.getState()!=null||!"".equals(label.getState())){
                    Predicate predicate=cb.equal(root.get("state").as(String.class),label.getState());
                    list.add(predicate);
                }
                //new 一个List集合。来存放所有的条件
               Predicate[]parr= new Predicate[list.size()];
                //把list直接转换成数组
                parr=list.toArray(parr);
                return cb.and(parr);//where labelname like"%小明%" and state="1"
            }
        });
    }
    public Page<Lable> pageQuery(Lable label,int page,int size) {
        Pageable pageable= PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Lable>() {
            /**
             *
             * @param root 根对象，也就是要把条件封装到哪个对象中，where类名=label.getid
             * @param query 封装的都是查询关键字，比如group by order by等
             * @param cb 用来封装条件对象的，如果直接返回null,表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Lable> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list=new ArrayList<>();
                if(label.getLabelname()!=null&&!"".equals(label.getLabelname())){
                    Predicate predicate=cb.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%");//where labelname like
                    list.add(predicate);
                }
                if(label.getState()!=null||!"".equals(label.getState())){
                    Predicate predicate=cb.equal(root.get("state").as(String.class),label.getState());
                    list.add(predicate);
                }
                //new 一个List集合。来存放所有的条件
                Predicate[]parr= new Predicate[list.size()];
                //把list直接转换成数组
                parr=list.toArray(parr);
                return cb.and(parr);//where labelname like"%小明%" and state="1"
            }
        },pageable);
    }
}

