create user ja_034 identified by "cometrue"; //사용자 생성

grant connect, resource to ja_034; //사용자 권한 부여

select * from tab;

drop table ja_034_member;

create table ja_034_member(
	email	varchar2(30) not null primary key,
	pw		varchar2(20) not null,
	name	varchar2(20),
	phone	varchar2(20)
);
update ja_034_member set name='hjh1', phone='7625' where email='gkswjdgns1@naver.com';
select * from ja_034_member where email='gkswjdgns1@naver.com' and pw='cometrue';
select * from ja_034_member where email = '1' and pw = '1'

insert into ja_034_member(email, pw, name, phone) 
values('gkswjdgns1@naver.com', 'cometrue', '한정훈1', '7625');
insert into ja_034_member(email, pw, name) 
values('gkswjdgns2@naver.com', 'cometrue', '한정훈2');
insert into ja_034_member(email, pw, name) 
values('gkswjdgns3@naver.com', 'cometrue', '한정훈3');
insert into ja_034_member(email, pw, name, phone) 
values('1', '1', '1', '1');

select * from ja_034_member;
select * from ja_034_product;

create table ja_034_product(
	pno		number(7) not null primary key,
	name		varchar2(30) not null,
	price		number(10) not null,
	cno			varchar2(10) not null,
	color		varchar2(20),
	psize		varchar2(20),
	regdate		date,
	image		varchar2(50)
);
create sequence p_seq INCREMENT BY 1 START WITH 1000000; //auto_increment
drop table ja_034_product;
drop sequence p_seq;

select * from ja_034_product;
select image from ja_034_product;
select rownum rnum, pno, name, price, cno, image from ja_034_product;
select rownum rnum, pno, name, price, cno, regdate, image from ja_034_product order by pno asc
select rownum rnum, pno, name, price, cno, regdate, image from ja_034_product order by name desc

select pno, name, price, cno, image from ja_034_product 
a where a.rum between 1 and 3



insert into ja_034_product(pno, name, price, cno, image) 
values(p_seq.nextval, 'item01', 50000, 'kids', 'bridge1.png');


delete ja_034_product where name = 'math';

