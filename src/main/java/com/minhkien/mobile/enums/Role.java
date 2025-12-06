package com.minhkien.mobile.enums;

public enum Role {

    ADMIN,
    USER
}

//Xét trong dự án thực tế sẽ phân cấp ra nhiều role và quyền khác nhau
//  ví dụ trong FB thì là người dùng nhưng trong 1 nhóm thì staff lại có nhiều quyền hạn hơn member
// User -> many Role
//              Role -> many Permisson
