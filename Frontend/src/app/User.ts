export interface UserRegister{
    role:string,
    name:string,
    phone:string,
    email:string,
    password:string,
    numberOfLeaves:number,
    remainingLeaves:number
}
export interface UserLogin{
    email:string,
    password:string,
}
export interface Leave{
    id:Number,
    name:string,
    email:string,
    type:string,
    startDate:string,
    endDate:string,
    reason:string,
    status:string,
    message:string,
}