export class GlobalVariables
{

  private _isLoggedIn: boolean = false;
  private static instance: GlobalVariables;

  private constructor() { }

  public static getInstance(): GlobalVariables {
    if (!GlobalVariables.instance) {
      GlobalVariables.instance = new GlobalVariables();
    }

    return GlobalVariables.instance;
  }
/*
  getLoggedInStatus(): boolean {
    return this._isLoggedIn;
  }

  setLoggedInStatus(value: boolean) {
    this._isLoggedIn = value;
  }*/

  get status() { return this._isLoggedIn };
  set status(value: boolean) {
    this._isLoggedIn = value;
    debugger;
  }


}
